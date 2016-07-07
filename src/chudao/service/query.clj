(ns chudao.service.query
  (:require [chudao.persistence.query :as persist-query]
            [io.pedestal.http :as bootstrap]
            [chudao.persistence.tag :as persist-tag]
            [chudao.service.data :as data]
            [clojure.string :as str]))

(defn find-files-by-user-ids
  [request]
  (let [user-id (get-in request [:json-params :user-id])
        result (persist-query/find-files-by-user-id user-id)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))

(defn find-product-by-tags
  [request]
  (let [logic-op (get-in request [:json-params :logic-operation])
        tags (get-in request [:json-params :product-tags])
        result (persist-query/find-products-by-tags tags logic-op)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        (map? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))

(defn find-products-by-ids
  [request]
  (let [id-string (get-in request [:json-params :product-ids])
        ids (str/split id-string #",")
        result (persist-query/find-products-by-ids ids)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        (map? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))

(defn find-files-by-product-ids
  [request]
  (let [id-string (get-in request [:json-params :product-ids])
        ids (str/split id-string #",")
        result (persist-query/find-files-by-product-ids ids)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        (map? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))
