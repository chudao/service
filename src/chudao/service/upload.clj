(ns chudao.service.upload
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [ring.middleware.multipart-params :as multipart-params]
            [chudao.service.data :as data]
            [chudao.persistence.upload :as persist-upload]))

(defn upload-photo
  [request]
  (let [mprequest (multipart-params/multipart-params-request request)
        params (:multipart-params mprequest)]
        (persist-upload/upload-file params))
  (bootstrap/json-response data/upload-success))

