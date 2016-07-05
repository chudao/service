(ns chudao.persistence.query
  (:import (java.sql SQLException))
  (:require [amazonica.aws.s3 :as s3]
            [korma.core :as korma]
            [chudao.persistence.tag :as persist-tag]
            [clojure.string :as str]))

(korma/defentity FileUpload)
(korma/defentity Product)
(korma/defentity ProductTag)

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

(defn- union-query
  [tag-ids]
  (korma/select ProductTag
                (korma/modifier "DISTINCT")
                (korma/fields :ProductId)
                (korma/where (in :TagId tag-ids))))

(defn- parse-result
  [result]
  (let [ids (reduce
              (fn [m v]
                (conj m (:ProductId v)))
              []
              result)]
    {:ProductIds ids}))

(defn find-products-by-tags
  [tags logic-op]
  (->
    tags
    (str/split #",")
    persist-tag/get-tag-ids
    union-query
    parse-result))
