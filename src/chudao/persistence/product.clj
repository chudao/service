(ns chudao.persistence.product
  (:import (java.sql SQLException))
  (:require [korma.core :as korma]
            [clojure.string :as str]
            [chudao.persistence.tag :as persist-tag])
  )

(korma/defentity Product)

(defn add
  [body]
  (let [data {:ProductName (:product-name body)
              :ProductBrand (:product-brand body)
              :ProductDescription (:product-description body)
              :ProductLink (:product-link body)
              :BrandLink (:brand-link body)}
        tags (str/split (:product-tags body) #",")]
    (try
      (let [product-id (->
                         (korma/insert Product
                                       (korma/values data))
                         :generated_key)]
        (persist-tag/insert-tags product-id tags)
        product-id)
      (catch SQLException e
        (prn e)
        (case (.getErrorCode e)
          :genric-error)))))
