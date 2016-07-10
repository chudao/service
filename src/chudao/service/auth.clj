(ns chudao.service.auth
  (:import (java.util UUID))
  (:require [clojure.data.json :as json]
            [chudao.service.data :as data]
            [chudao.persistence.auth :as persist-auth]
            [chudao.cache.user :as user]
            [io.pedestal.http :as bootstrap]
            [ring.util.response :as ring-resp]))

(defn- generate-session
  []
  (str (UUID/randomUUID)))

(defn login
  [request]
  (let [body (:json-params request)
        user-name (:user-name body)
        password (:password body)
        result (persist-auth/login user-name password)
        session (generate-session)]
    (if (map? result)
      (do
          (user/put-user-in-cache session result)
          (->
            (bootstrap/json-response (data/login-success result))
            (ring-resp/header "X-Auth-Token" session)))
      (bootstrap/json-response data/login-failure)
      )))

(defn register
  [request]
  (let [body (:json-params request)
        user-name (:user-name body)
        user-category (:user-category body "user")
        password (:password body)
        result (persist-auth/register user-name user-category password)
        session (generate-session)]
    (cond
      (map? result) (do
                      (user/put-user-in-cache session result)
                      (->
                        (bootstrap/json-response (data/register-success result))
                        (ring-resp/header "X-Auth-Token" session)))
      (= result :duplicate) (bootstrap/json-response data/register-failure-duplicate)
      (= result :generic-error) (bootstrap/json-response data/register-failure-general)
      )))
