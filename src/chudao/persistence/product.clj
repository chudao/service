(ns chudao.persistence.product
  (:import (java.sql SQLException))
  (:require [korma.core :as korma])
  )

(korma/defentity Product)

(defn add
  [body]
  (let [data {:ProductName (:product-name body)
              :ProductBrand (:product-brand body)
              :ProductDescription (:product-description body)
              :ProductLink (:product-link body)
              :BrandLink (:brand-link body)}]
    (try
      (->
        (korma/insert Product (korma/values data))
        :generated_key)
      (catch SQLException e
        (prn e)
        (case (.getErrorCode e)
          :genric-error)))))
