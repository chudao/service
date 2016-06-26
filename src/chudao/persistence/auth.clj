(ns chudao.persistence.auth
  (:import [java.sql SQLException])
  (:require [korma.core :as korma]))

(korma/defentity User)

(defn login
  [username password]
  (->
    (korma/select User
                  (korma/fields :UserId :UserName)
                  (korma/where {:UserName username :Password password}))
    first))

(defn register
  [username password]
  (try
    (korma/insert User
                  (korma/values {:UserName username :Password password}))
    (catch SQLException e
      (if (= 1062 (.getErrorCode e))
          :duplicate))))


