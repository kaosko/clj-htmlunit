(ns clj-htmlunit.html-element
  (:import [com.gargoylesoftware.htmlunit.html
            HtmlElement]))


(defprotocol IHtmlElement
  (click 
    [element]
    [element shift? ctrl? alt?])
  (double-click
    [element]
    [element shift? ctrl? alt?])
  (id [element])
  (node-name [element])
  (type! 
    [element text]
    [element text shift? ctrl? alt?])
  (element-by-id [element id])
  (elements-by-attribute [element element-name attribute-name attribute-value])
  (enclosing-element [element tag-name])
  (children [element])
  (canonical-xpath [element])
  (focus! [element])
  (query-selector [element selectors])
  (query-selector-all [element selectors]))

(extend-type HtmlElement
  IHtmlElement
  (click 
    ([element] (.click element))
    ([element shift? ctrl? alt?] (.click element shift? ctrl? alt?)))
  (double-click
    ([element] (.doubleClick element))
    ([element shift? ctrl? alt?] (.doubleClick element shift? ctrl? alt?)))
  (id [element] (.getId element))
  (node-name [element] (.getNodeName element))
  (type! 
    ([element text] (.type element text))
    ([element text shift? ctrl? alt?] (.type element text shift? ctrl? alt?)))
  (element-by-id [element id] (.getElementById element id))
  (elements-by-attribute [element element-name attribute-name attribute-value]
                         (.getElementsByAttribute element element-name attribute-name attribute-value))
  (enclosing-element [element tag-name] (.getEnclosingElement element tag-name))
  (children [element] (.getChildElements element))
  (canonical-xpath [element] (.getCanonicalXPath element))
  (focus! [element] (.focus element))
  (query-selector [element selectors] (.querySelector element selectors))
  (query-selector-all [element selectors] (.querySelectorAll element selectors)))


