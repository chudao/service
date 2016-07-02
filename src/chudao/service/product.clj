(ns chudao.service.product
  (:require [ring.middleware.multipart-params :as multipart-params]
            [chudao.persistence.product :as persist-product]
            [io.pedestal.http :as bootstrap]
            [chudao.service.data :as data]))


(defn add
  [request]
  (let [data (:json-params request)
        result (persist-product/add data)]
    (bootstrap/json-response
      (cond
        (number? result) (data/product-add-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists
        )
      )))

;; needed for form post
(defn add-multiform
  [request]
  (let [mprequest (multipart-params/multipart-params-request request)
        params (:multipart-params mprequest)
        result (persist-product/add {:product-name (get params "product-name")
                                     :product-brand (get params "product-brand")
                                     :product-description (get params "product-description")
                                     :product-link (get params "product-link")
                                     :brand-link (get params "brand-link")})]
    (bootstrap/json-response
      (cond
        (number? result) (data/product-add-success result)
        ;(= result :user-id-not-exists) data/upload-failure-user-id-not-exists
        )
      )))
