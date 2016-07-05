(ns chudao.persistence.query
  (:import (java.sql SQLException))
  (:require [amazonica.aws.s3 :as s3]
            [korma.core :as korma]))

(korma/defentity FileUpload)
(korma/defentity Product)

;;;; may need error handling...what happens when user-id not valid? currently same success result
(defn find-files-by-user-id
  [user-id]
  (->
    (korma/select FileUpload
                  ;;(korma/fields :FileId :FileName :FileKey)
                  (korma/where {:UserId user-id}))))

(defn find-products-by-ids
  [ids]
  (try
    (korma/select Product
                  (korma/where (in :ProductId ids)))
    (catch SQLException e
      (prn e)
      (case (.getErrorCode e)
        :genric-error))))

(defn find-files-by-product-ids
  [ids]
  (try
    (korma/select FileUpload
                  (korma/where (in :ProductId ids)))
    (catch SQLException e
      (prn e)
      (case (.getErrorCode e)
        :genric-error))))