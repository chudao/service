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
    (try
      (if (password/check password encrypted-password)
        user-data)
      (catch Exception e
        :genric-error))))

(defn register
  [username password]
  (try
    (let [user-id (->
                    (korma/insert User
                                  (korma/values {:UserName username :Password (password/encrypt password)}))
                    :generated_key)]
      {:UserId user-id
       :UserName username})
    (catch SQLException e
      (case (.getErrorCode e)
        1062 :duplicate
        :genric-error))))


