(ns chudao.service.query
  (:require [chudao.persistence.query :as persist-query]
            [io.pedestal.http :as bootstrap]
            [chudao.persistence.tag :as persist-tag]
            [chudao.service.data :as data]
            [clojure.string :as str]
            [chudao.cache.user :as user]))

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

(defn find-request-by-user-id
  [request]
  (let [user-id (user/get-user-id (get-in request [:headers "x-auth-token"]))
        result (persist-query/find-request-by-user-id user-id)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        (map? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))

(defn- filter-by-status
  [temp-result status]
  (if (or (= status "responded") (= status "unresponded"))
    (filter #(= status (:status %)) temp-result)
    temp-result
  ))

(defn find-request-by-user-id-and-status
  [request]
  (let [user-id (user/get-user-id (get-in request [:headers "x-auth-token"]))
        status (get-in request [:json-params :status])
        temp-result (persist-query/find-request-by-user-id user-id)
        result (filter-by-status temp-result status)]
    (bootstrap/json-response
      (cond
        (seq? result) (data/query-success result)
        (map? result) (data/query-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists))))
        ))))


