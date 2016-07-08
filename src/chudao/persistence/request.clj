(ns chudao.persistence.request
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defonce mongo-url (System/getenv "MONGODB_URI"))

(defn add
  [user-id user-message file-key]
  (let [{:keys [conn db]} (mg/connect-via-uri mongo-url)]
    (mc/insert-and-return db
                          "UserRequest"
                          {:user-id user-id
                           :user-message user-message
                           :file-key file-key})))
