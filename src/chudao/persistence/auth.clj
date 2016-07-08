(ns chudao.persistence.auth
  (:import [java.sql SQLException])
  (:require [korma.core :as korma]
            [crypto.password.scrypt :as password]))

(korma/defentity User)

(defn login
  [username password]
  (let [user-data (first (korma/select User
                                       (korma/fields :UserId :UserName :Password)
                                       (korma/where {:UserName username})))
        encrypted-password (:Password user-data)]
    (if (password/check password encrypted-password)
      user-data)))

(defn register
  [username password]
  (try
    (->
      (korma/insert User
                  (korma/values {:UserName username :Password (password/encrypt password)}))
      :generated_key)
    (catch SQLException e
      (case (.getErrorCode e)
        1062 :duplicate
        :genric-error))))


