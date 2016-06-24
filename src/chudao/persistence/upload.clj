(ns chudao.persistence.upload
  (:require [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]))

(defn- uuid [] (str (java.util.UUID/randomUUID)))

(defn- put-product-data
  [data]
  (prn data))

(defn upload-file
  [params]
  (let [file (get params "file")
        file-name (str (uuid) "---" (:filename file))]
    (put-product-data {:user-name (get params "user-name")
                       :product-name (get params "product-name")
                       :product-brand (get params "product-brand")
                       :product-category (get params "product-category")
                       :product-description (get params "product-description")
                       :product-link (get params "product-link")
                       :brand-site (get params "brand-site")
                       :photo-key [file-name]})
    (s3/put-object :bucket-name "chudao-photos"
                   :key file-name
                   :metadata {:content-length (:size file)
                              :content-type (:content-type file)}
                   :file (:tempfile file))))

