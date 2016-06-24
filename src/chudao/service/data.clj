(ns chudao.service.data)

(def login-success
  {:response-code "000"
   :response-message "login success"
   :auth-token "womenchudaola"})

(def login-failure
  {:response-code "001"
   :response-message "login failure: user not exists or password incorrect"})

(def register-failure
  {:response-code "011"
   :response-message "registration failure: user already exists"})

(def register-success
  {:response-code "010"
   :response-message "registration success"
   :auth-token "womenchudaola"})

(def upload-success
  {:response-code "020"
   :response-message "upload success"})
