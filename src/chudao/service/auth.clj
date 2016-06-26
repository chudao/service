(ns chudao.service.auth
  (:require [clojure.data.json :as json]
            [chudao.service.data :as data]
            [chudao.persistence.auth :as persist-auth]
            [io.pedestal.http :as bootstrap]
            [ring.util.response :as ring-resp]))

(defn login
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)
        result (persist-auth/login username password)]
    (bootstrap/json-response
      (if result
        (data/login-success result)
        data/login-failure))))

(defn register
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)
        result (persist-auth/register username password)]
    (bootstrap/json-response
      (cond
        (map? result) (data/register-success result)
        (= result :duplicate) data/register-failure-duplicate))))
