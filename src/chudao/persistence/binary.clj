(ns chudao.persistence.binary
  (:import [java.sql SQLException])
  (:require [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]
            [korma.core :as korma]))

(korma/defentity FileUploadInfo)

(defn- uuid [] (str (java.util.UUID/randomUUID)))

(defn- put-file-metadata
  [data]
  (try
    (korma/insert FileUploadInfo
                  (korma/values data))
    (catch SQLException e
      (case (.getErrorCode e)
        1452 :user-id-not-exists
        :genric-error))))

(defn upload-file
  [params]
  (let [file (get params "file")
        file-key (str (uuid) "---" (:filename file))
        result (put-file-metadata {:UserId (get params "user-id")
                                   :ProductName (get params "product-name")
                                   :ProductBrand (get params "product-brand")
                                   :ProductCategory (get params "product-category")
                                   :ProductDescription (get params "product-description")
                                   :ProductLink (get params "product-link")
                                   :BrandLink (get params "brand-link")
                                   :FileName (:filename file)
                                   :FileKey [file-key]})]
    (if-not (keyword? result)
      (s3/put-object :bucket-name "chudao-photos"
                     :key file-key
                     :metadata {:content-length (:size file)
                                :content-type (:content-type file)}
                     :file (:tempfile file))
      result)))

(defn download-file
  [file-name]
  (let [object (s3/get-object :bucket-name "chudao-photos" :key file-name)]
    {:file (:object-content object)
     :content-type (get-in object [:object-metadata :content-type])}))

