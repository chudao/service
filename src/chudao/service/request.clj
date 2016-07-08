(ns chudao.service.request
  (:require [chudao.persistence.request :as persist-request]
            [chudao.service.data :as data]
            [io.pedestal.http :as bootstrap]))

(defn add
  [request]
  (let [data (:json-params request)
        user-id (:user-id data)
        user-message (:user-message data)
        file-key (:file-key data)
        result (persist-request/add user-id user-message file-key)]
    (bootstrap/json-response
      (cond
        (map? result) (data/request-add-success result)
        ))))
