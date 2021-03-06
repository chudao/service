(ns chudao.service.request
  (:require [chudao.persistence.request :as persist-request]
            [chudao.service.data :as data]
            [chudao.cache.user :as user]
            [io.pedestal.http :as bootstrap]))

(defn add
  [request]
  (let [data (:json-params request)
        user-id (user/get-user-id (get-in request [:headers "x-auth-token"]))
        result (persist-request/add (assoc data :user-id user-id :status "unresponded"))]
    (bootstrap/json-response
      (cond
        (map? result) (data/request-add-success result)
        ))))

(defn handle
  [request]
  (let [data (:json-params request)
        user-id (user/get-user-id (get-in request [:headers "x-auth-token"]))
        request-id (:request-id data)
        product-ids (:product-ids data)
        result (persist-request/handle user-id request-id product-ids)]
    (bootstrap/json-response
      (cond
        (string? result) (data/request-handle-success result)
        ))))
