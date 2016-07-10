(ns chudao.persistence.auth
  (:import [java.sql SQLException])
  (:require [korma.core :as korma]
            [chudao.persistence.transformer :as transformer]
            [crypto.password.scrypt :as password]))

(korma/defentity User)

(defn login
  [user-name password]
  (let [user-data (first (korma/select User
                                       (korma/fields :UserId :UserName :UserCategory :Password)
                                       (korma/where {:UserName user-name})))
        encrypted-password (:Password user-data)]
    (try
      (if (password/check password encrypted-password)
        (let [result (transformer/transform-sql->clj user-data)]
          (prn result)
          result))
      (catch Exception e
        :generic-error))))

(defn register
  [user-name user-category password]
  (try
    (let [user-id (->
                    (korma/insert User
                                  (korma/values {:UserName user-name
                                                 :UserCategory user-category
                                                 :Password (password/encrypt password)}))
                    :generated_key)]
      {:user-id user-id
       :user-name user-name
       :user-category user-category})
    (catch SQLException e
      (case (.getErrorCode e)
        1062 :duplicate
        :generic-error))))


