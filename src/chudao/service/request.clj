(ns chudao.service.request
  (:require [chudao.persistence.request :as persist-request]
            [chudao.service.data :as data]
            [io.pedestal.http :as bootstrap]))

(defn add
  [request]
  (let [data (:json-params request)
        result (persist-request/add data)]
    (bootstrap/json-response
      (cond
        (map? result) (data/request-add-success result)
        ))))
