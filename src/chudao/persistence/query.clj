(ns chudao.persistence.query
  (:require [amazonica.aws.s3 :as s3]
            [korma.core :as korma]))

(korma/defentity FileUploadInfo)

;;;; may need error handling...what happens when user-id not valid? currently same success result
(defn by-user-id
  [user-id]
  (->
    (korma/select FileUploadInfo
                  ;;(korma/fields :FileId :FileName :FileKey)
                  (korma/where {:UserId user-id}))))
