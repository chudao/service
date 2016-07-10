(ns chudao.service.auth
  (:import (java.util UUID))
  (:require [clojure.data.json :as json]
            [chudao.service.data :as data]
            [chudao.persistence.auth :as persist-auth]
            [io.pedestal.http :as bootstrap]
            [ring.util.response :as ring-resp]))

(defn- generate-session
  []
  (str (UUID/randomUUID)))

(defn login
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)
        result (persist-auth/login username password)
        session (generate-session)]
    (if result
      (do
          (data/put-user-in-cache session result)
          (->
            (bootstrap/json-response (data/login-success result))
            (ring-resp/header "X-Auth-Token" session)))
      (bootstrap/json-response data/login-failure)
      )))

(defn register
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)
        result (persist-auth/register username password)
        session (generate-session)]
    (cond
      (map? result) (do
                      (data/put-user-in-cache session result)
                      (->
                        (bootstrap/json-response (data/register-success result))
                        (ring-resp/header "X-Auth-Token" session)))
      (= result :duplicate) (bootstrap/json-response data/register-failure-duplicate)
      )))
