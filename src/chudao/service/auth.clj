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
        password (:password body)]
    (if (persist-auth/login username password)
      (bootstrap/json-response data/login-success)
      (bootstrap/json-response data/login-failure))))

(defn register
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)]
    (if (persist-auth/register username password)
      (bootstrap/json-response data/register-success)
      (bootstrap/json-response data/register-failure))))
