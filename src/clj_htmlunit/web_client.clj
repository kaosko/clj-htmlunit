(ns clj-htmlunit.web-client
  (:import [com.gargoylesoftware.htmlunit
            WebClient]))

(defn make-web-client [& {:keys [request-headers
                                 java-script?
                                 redirect?
                                 css?
                                 timeout
                                 disable-logging?]}]
  (let [client (WebClient.)]
    (when request-headers
      (doseq [elem request-headers]
        (.addRequestHeader client (key elem) (val elem))))
    (when (not (= nil redirect?))
      (.setRedirectEnabled client redirect?))
    (when (not (= nil timeout))
      (.setTimeout client timeout))
    (when java-script?
      (.setJavaScriptEnabled client java-script?))
    (when (not (= nil css?))
      (.setCssEnabled client css?))
    (when disable-logging?
      (-> 
        (org.apache.commons.logging.LogFactory/getFactory)
        (.setAttribute "org.apache.commons.logging.Log" "org.apache.commons.logging.impl.NoOpLog"))
      
      (->
        (java.util.logging.Logger/getLogger "com.gargoylesoftware.htmlunit")
        (.setLevel java.util.logging.Level/OFF))
      (->
        (java.util.logging.Logger/getLogger "org.apache.commons.httpclient")
        (.setLevel java.util.logging.Level/OFF)))
    client))

(defprotocol IWebClient
  (page 
    [client url]
    [client url wait-for-javascript])
  (close-all [client]))

(extend-type WebClient
  IWebClient
  (page 
    ([client url] (.getPage client url))
    ([client url wait-for-javascript]
      (let [result (page client url)]
        (.waitForBackgroundJavaScript client wait-for-javascript)
        result)))
  (close-all [client] (.closeAllWindows client)))


      
                                

