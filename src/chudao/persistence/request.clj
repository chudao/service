(ns chudao.persistence.request
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [chudao.util.random :as random]))

(defonce mongo-url (System/getenv "MONGODB_URI"))

(defn add
  [data]
  (let [{:keys [conn db]} (mg/connect-via-uri mongo-url)]
    (mc/insert-and-return db
                          "UserRequest"
                          (select-keys data [:user-id :user-message :file-key :budget :product-tags])
                          )))


(defn handle
  [user-id request-id product-ids]
  (random/uuid)
  ;; remove request data from Mongl
  ;; insert request handling data to MySQL
  )
