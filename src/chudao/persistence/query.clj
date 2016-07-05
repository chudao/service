(ns chudao.persistence.query
  (:import (java.sql SQLException))
  (:require [amazonica.aws.s3 :as s3]
            [korma.core :as korma]))

(korma/defentity FileUploadInfo)
(korma/defentity Product)

;;;; may need error handling...what happens when user-id not valid? currently same success result
(defn by-user-id
  [user-id]
  (->
    (korma/select FileUploadInfo
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
