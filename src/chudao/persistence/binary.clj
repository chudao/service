(ns chudao.persistence.binary
  (:require [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]))

(defn- uuid [] (str (java.util.UUID/randomUUID)))

(defn- put-product-data
  [data]
  (prn data))

(defn upload-file
  [params]
  (let [file (get params "file")
        file-key (str (uuid) "---" (:filename file))]
    (put-product-data {:user-name (get params "user-name")
                       :product-name (get params "product-name")
                       :product-brand (get params "product-brand")
                       :product-category (get params "product-category")
                       :product-description (get params "product-description")
                       :product-link (get params "product-link")
                       :brand-site (get params "brand-site")
                       :file-name (:filename file)
                       :file-key [file-key]})
    (s3/put-object :bucket-name "chudao-photos"
                   :key file-key
                   :metadata {:content-length (:size file)
                              :content-type (:content-type file)}
                   :file (:tempfile file))))

(defn download-file
  [file-name]
  (let [object (s3/get-object :bucket-name "chudao-photos" :key file-name)]
    {:file (:object-content object)
     :content-type (get-in object [:object-metadata :content-type])}))

