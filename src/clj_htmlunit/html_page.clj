(ns clj-htmlunit.html-page
  (:import [com.gargoylesoftware.htmlunit.html
            HtmlPage]))


(defn get-page [client url]
  (let [page (.getPage client url)]
    (.waitForBackgroundJavaScript client 5000)
    page))

(defprotocol IHtmlPage
  (document-element [page])
  (element-by-id [page id])
  (element-by-id-and-or-name [page id-and-or-name])
  (element-by-name [page name])
  (elements-by-name [page name])
  (elements-by-tag-name [page tag-name])
  (form-by-name [page name])
  (anchor-by-name [page name])
  (anchor-by-text [page text])
  (anchor-by-href [page href])
  (html-element-by-xpath [page xpath])
  (frame-by-name [page name])
  (frames [page])
  (focused-element [page])
  (title-text [page])
  (execute-java-script! [page source]))

(extend-type HtmlPage 
  IHtmlPage
  (document-element [page] (.getDocumentElement page))
  (element-by-id [page id] (.getElementById page id))
  (element-by-id-and-or-name [page id-and-or-name]
                             (.getElementByIdAndOrName page id-and-or-name))
  (element-by-name [page name] (.getElementByName page name))
  (elements-by-name [page name] (.getElementsByName page name))
  (elements-by-tag-name [page tag-name]
                        (.getElementsByTagName page name))
  (form-by-name [page name] (.getFormByName page name))
  (anchor-by-name [page name] (.getAnchorByName page name))
  (anchor-by-text [page text] (.getAnchorByText page text))
  (anchor-by-href [page href] (.getAnchorByHref page href))
  (html-element-by-xpath [page xpath]
                         (.getHtmlElementByXpath page xpath))
  (frame-by-name [page name] (.getFrameByName page name))
  (frames [page] (.getFrames page))
  (focused-element [page] (.getFocusedElement page))
  (title-text [page] (.getTitleText page))
  (execute-java-script! [page source] (.executeJavaScript page source)))


