(ns chudao.auth
  (:require [clojure.data.json :as json]
            [chudao.data :as data]
            [chudao.persistence :as persistence]
            [io.pedestal.http :as bootstrap]
            [ring.util.response :as ring-resp]))

(defn login
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)]
    (if (persistence/login username password)
      (bootstrap/json-response data/login-success)
      (bootstrap/json-response data/login-failure))))

(defn register
  [request]
  (let [body (:json-params request)
        username (:username body)
        password (:password body)]
    (if (persistence/register username password)
      (bootstrap/json-response data/register-success)
      (bootstrap/json-response data/register-failure))))
