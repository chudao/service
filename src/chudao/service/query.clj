(ns chudao.service.query
  (:require [chudao.persistence.query :as persist-query]
            [io.pedestal.http :as bootstrap]
            [chudao.service.data :as data]))

(defn by-user-id
  [request]
  (let [user-id (get-in request [:path-params :user-id])
        result (persist-query/by-user-id user-id)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))

