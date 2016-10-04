String.prototype.trim = function()
{
    return this.replace(/(^[ \f\n\r\t]*)|([ \f\n\r\t]*$)/g, "");
}
String.prototype.comma=Number.prototype.comma=function()
{
   var num = String(this).replace(/\,/g, '');
   num = num.trim();

   var strFloat = "";
   
   if( isNumber(num) ) {
      var isMinus = num.charAt(0) == '-';
      if( isMinus ) num = num.substring(1);

      var dotPos = num.indexOf(".");
      if( dotPos >= 0 ) {
         strFloat = num.substring(dotPos);
         num = num.substring(0,dotPos);
      }

      var newNum = num.match(RegExp("^[0-9]{"+(num.length%3||3)+"}|[0-9]{3}","g"));

      num = newNum.toString();

      if( strFloat.length > 0 ) num += strFloat;
      if( isMinus ) num = "-" + num;
   }

   return num;
}
var StringUtil = {
   replaceAll : function(origin, from, to) {
      while( origin.indexOf(from) >= 0 ) origin = origin.replace(from, to);
      return origin;
   },
   lpad : function(val, paddingChar, count) {
	  if( typeof val != "string" ) val += "";
      for(var i = val.length; val.length < count && i < count; i++ ) val = paddingChar + val;
      if( val.length > count ) val = val.substring(val.length - count, val.length);
      return val;
   },
   rpad : function(val, paddingChar, count) {
	  if( typeof val != "string" ) val += "";
      for(var i = val.length; val.length < count && i < count; i++ ) val += paddingChar;
      if( val.length > count ) val = val.substring(0, count);
      return val;
   },
   // 0023
   ltrim : function(val, trimChar) {
      if( typeof val != "string" ) val += "";
      var pos = 0;
      for( var i = 0; i < val.length; i++ ) {
         var ch = val.charAt(i);
         
         if( null == trimChar ) {
            if( ' ' != ch && '\n' != ch && '\r' != ch ) {
               break;
            }
         } else {
            if( trimChar != ch ) {
               break;
            }
         }
         pos += 1;
      }
      val = val.substring(pos);
      return val;
   },
   // 2300
   rtrim : function(val, trimChar) {
      if( typeof val != "string" ) val += "";
      var pos = val.length;
      for( var i = val.length - 1; i >= 0; i-- ) {
         var ch = val.charAt(i);
          
         if( null == trimChar ) {
            if( ' ' != ch && '\n' != ch && '\r' != ch ) {
               break;
            }
         } else {
            if( trimChar != ch ) {
               break;
            }
         }
         pos -= 1;
      }
      val = val.substring(0, pos);
      return val;
   },
   trim : function(val, trimChar) {
      val = StringUtil.ltrim(val, trimChar);
      val = StringUtil.rtrim(val, trimChar);
      return val;
   }
}
var DateHandler = {
   toString : function(dt, dateFormat) {
      var str = dateFormat.toUpperCase();
      
      if( dateFormat.indexOf("YYYY") >= 0 ) str = StringUtil.replaceAll(str, "YYYY", dt.getFullYear());
      if( dateFormat.indexOf("MM") >= 0 )   str = StringUtil.replaceAll(str, "MM", StringUtil.lpad(dt.getMonth() + 1, "0", 2));
      if( dateFormat.indexOf("DD") >= 0 )   str = StringUtil.replaceAll(str, "DD", StringUtil.lpad(dt.getDate(), "0", 2));
      if( dateFormat.indexOf("HH24") >= 0 ) str = StringUtil.replaceAll(str, "HH24", StringUtil.lpad(dt.getHours(), "0", 2));
      if( dateFormat.indexOf("MI") >= 0 ) str = StringUtil.replaceAll(str, "MI", StringUtil.lpad(dt.getMinutes(), "0", 2));
      if( dateFormat.indexOf("SS") >= 0 ) str = StringUtil.replaceAll(str, "SS", StringUtil.lpad(dt.getSeconds(), "0", 2));
      
      return str;
   }
}
function loadPage(__url__, param, successHandler, errorHandler, async) {
   var __param__ = null != param && param.length > 0 ? param : "dummy";
   var __async__ = null != async ? async : true;
   var __successHandler__ = null != successHandler ? successHandler : function(val) {};
   var __errorHandler__ = null != errorHandler ? errorHandler : function(err) { alert("error : " + err.status); };

   var __retVal__ = $.ajax({
      type     : "POST",
      url      : __url__,
      //dataType : "html",
      cache    : false,
      async    : __async__,
      data     : __param__,
      error    : __errorHandler__,
      success  : __successHandler__
   }).responseText;

   if( __async__ ) {
      return "";
   } else {
      return __retVal__;
   }
}

function submitPage(__url__, param, pageOption) {
   return getPage(__url__, param, pageOption);
}
function getPage(__url__, param, pageOption) {
   var __param__ = null == param || "" == param ? "dummy" : param;
   var __errorHandler__ = function(err) { alert("error : " + err.status); };

   var __retVal__ = null;
   
   var __pageOption__ = {
      type     : "POST",
      url      : __url__,
      //dataType : "html",
      cache    : false,
      async    : false,
      data     : __param__,
      error    : __errorHandler__,
      success  : function(repVal) { __retVal__ = repVal; }
   };
   
   if( null != pageOption ) {
      __pageOption__ = pageOption;
      
      __pageOption__.url = __url__;
      __pageOption__.data = __param__;
      if( null == __pageOption__.type ) __pageOption__.type = "POST";
      if( null == __pageOption__.cache ) __pageOption__.cache = false;
      if( null == __pageOption__.async ) __pageOption__.async = false;
      if( null == __pageOption__.error ) __pageOption__.error = __errorHandler__;
      __pageOption__.success = function(repVal) { __retVal__ = repVal; }
   }

   $.ajax(__pageOption__);

   return __retVal__;
}
function submitForm(__form_id__, __response_function__) {

   var __retVal__ = "";
   var __successHandler__ = null != __response_function__ ? __response_function__ : function(repVal) { __retVal__ = repVal; };

   var __options__ = { 
      //beforeSubmit:  showRequest,  // pre-submit callback 
      success:       __successHandler__,  // post-submit callback 
      async:         true,
      timeout:       300000 
 
      // other available options: 
      //url:       url         // override for form's 'action' attribute 
      //type:      type        // 'get' or 'post', override for form's 'method' attribute 
      //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
      //clearForm: true        // clear all form fields after successful submit 
      //resetForm: true        // reset the form after successful submit 
 
      // $.ajax options can be used here too, for example: 
      //timeout:   3000 
   }; 
 
   // bind form using 'ajaxForm' 
   $("#" + __form_id__).ajaxForm(__options__); 
   $("#" + __form_id__).submit();

//   $("#" + __form_id__).submit(function () {
//      $(this).ajaxSubmit(__options__); 
//      return false;
//   });

   return __retVal__;
}
function onlyNum(obj) {
   var value = obj.value;
   if( isNumber(value) ) obj.value = value.comma();
}
function toNumber(value, defaultValue) {
   if( null == defaultValue ) defaultValue = -2147483648; // integer minimum value : ?2,147,483,648
   
   if( isNumber(value) ) {
      value = "" + value; // convert to string
      var val = value.replace(/\,/g, '');

      if( val.indexOf('.') > 0 ) {
         return parseFloat(val);
      } else {
         return parseInt(val);
      }
   }

   return defaultValue;
}
function isNumber(value) {
   if( "number" == (typeof value) ) return true;
   
   value = value.trim();

   if( 0 == value.length ) return false;

   var isNumber = true;
   var dotFound = false;
   for( var i = 0; i < value.length; i++ ) {
      switch( value.charAt(i) ) {
         case '-' :
            if( i > 0 ) isNumber = false;
            if( 1 == value.length ) isNumber = false;
            break;
         case ',' : if( 0 == i || dotFound ) isNumber = false; break;
         case '.' : 
            if( 0 == i || dotFound ) {
               isNumber = false;
            } else {
               dotFound = true;
            }
            break;
         case '0' :
         case '1' :
         case '2' :
         case '3' :
         case '4' :
         case '5' :
         case '6' :
         case '7' :
         case '8' :
         case '9' :
            if( i > 0 && value.charAt(0) == '0' ) isNumber = false;
            break;

         default :
            isNumber = false;
      }

      if( ! isNumber ) break;
   }
   
   return isNumber;
}
function showModal(__url__, __width__, __height__, __data__, __feature__) {
   if( null == __data__ ) __data__ = "";
   if( null == __feature__ ) __feature__ = {};
   var feature = "dialogWidth:" + __width__ + "px; dialogHeight:" + __height__ + "px; location:no; resizable:no; status:no;";
   if( null == __feature__["scroll"] ) feature += "scroll:auto;";
   else feature += " scroll:" + __feature__["scroll"];
   
   // ����â���� __data__ ���� �������� var param = window.dialogArguments;
   //            ������ ���� window.returnValue = ${return content};
   return window.showModalDialog(__url__, __data__, feature);
}
var __WINDOW_NAME_SEQ__ = Math.round(Math.random() * 1000000) + 1;
function showModaless(__url__, __width__, __height__, __data__, __feature__) {
   if( null == __data__ ) __data__ = "";
   var feature = "width=" + __width__ + ", height=" + __height__ + ", toolbar=no, menubar=no, directories=no, location=no, statusbar=no";
   if( null == __feature__ || null == __feature__["scrollbars"] ) feature += ",scrollbars=yes";
   else feature += ",scrollbars=" + __feature__["scrollbars"];
   if( null == __feature__ || null == __feature__["resizable"] ) feature += ",resizable=yes";
   else feature += ",resizable=" + __feature__["resizable"];
   
   var windowName = "WND" + (++ __WINDOW_NAME_SEQ__);
   var retVal = window.open(__url__, windowName, feature);
   //retVal.name = windowName;
   //alert(windowName);
   return {WND_PTR : retVal, WND_NAME : windowName};
}

// ����� ��ȣ üũ
function checkBizNo(__no1__, __no2__,__no3__) {
   // ���� üũ
   if( 3 != __no1__.length ) return false;
   if( 2 != __no2__.length ) return false;
   if( 5 != __no3__.length ) return false;

   // ���� üũ
   for( i = 0; i < 3; i++ ) if( !('0' <= __no1__.charAt(i) && __no1__.charAt(i) <= '9') ) return false;
   for( i = 0; i < 2; i++ ) if( !('0' <= __no2__.charAt(i) && __no2__.charAt(i) <= '9') ) return false;
   for( i = 0; i < 5; i++ ) if( !('0' <= __no3__.charAt(i) && __no3__.charAt(i) <= '9') ) return false;

   var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
   var bizID = "" + __no1__ + __no2__ + __no3__;
   var i, sum=0, c2, remander;

   for( i = 0; i <= 7; i++ ) sum += checkID[i] * parseInt(bizID.charAt(i));

   c2 = "0" + (checkID[8] * parseInt(bizID.charAt(8)));
   c2 = c2.substring(c2.length - 2, c2.length);

   sum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));

   remander = (10 - (sum % 10)) % 10 ;

   if (Math.floor(bizID.charAt(9)) != remander) {
      return false;
   }else{
      return true;
   }
}
// ��ȿ�� �̸������� üũ
function checkValidEmail(__param__){
   //var reg = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
   var reg = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

   return reg.test(__param__);
}
function checkAll(name, flag) {
   $("input[name=" + name + "]").attr("checked", flag);
}
function getCheckedArray(name) {
   var valArr = [];
   $("input[name=" + name + "]:checked").each(function() { valArr.push(this.value)});
   return valArr;
}
function getCheckedCount(name) {
   return getCheckedArray(name).length;
}
function getCheckBoxCount(name) {
   return $("input[name=" + name + "]").length;
}
function setCookie(name, value, expire) {
   var today = new Date();
   if( null != expire && isNumber(expire) ) {
      today.setDate(today.getDate() + expire);
   }
   var strCookie = name + "=" + escape(value) + "; path=/; expires=" + today.toUTCString() + ";"
   //if( 0 == document.cookie.length ) {
      document.cookie = strCookie;
   //} else {
   //   document.cookie += ";" + strCookie;
   //}
}
function getCookie(name) {
   var str = document.cookie;
   var pos = str.indexOf(name + "=");
   if( pos >= 0 ) {
      str = str.substring(pos);
      pos = str.indexOf(";");
      if( pos < 0 ) pos = str.length;
      
      str = str.substring(name.length + 1, pos);
      
      return str;
   }
   
   return "";
}
function removeCookie(name) {
   setCookie(name, "", -1);
}