[#ftl]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/html">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet"
          type="text/css"
          media="screen"
          href="/css/main.css"/>
    <link rel="stylesheet"
          type="text/css"
          media="screen"
          href="/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          type="text/css"
          media="screen"
          href="/css/colorbox.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/jquery.colorbox.js"></script>
    <title>${msg['title']}</title>
  </head>
   <body>

   <script type="text/javascript">
     $(function () {
       $('a[rel=popup]').colorbox();
     });
   </script>
  <div id="header">
  </div>
  <div id="outer">
    <div id="content">
      <div id="registration">
         <div class="user">

        [#if session['principal']??]
          <a href="/book/~new">${msg['user.addressbook.add']}</a>
        [#else]
          <a  href="/auth/signup"> ${msg['user.registration']} </a>
        [/#if]

          <div class="errorslist">
          [#assign errors = flash['errors']!/]
          [#if errors??]
            <ul>
              [#list errors as e]
                <li>${e}</li>
              [/#list]
            </ul>
          [/#if]
          </div>


          <div class="msg ">
          ${flash['msg']!}
          </div>

          <div class="user" >
          [#if session['principal']??]
            <a class="linc btn-danger"   href="/auth/logout">${msg['user.logout.href']}</a>
          ${session['principal'].login!}
          [#else]
           <!-- <a class="linc btn-success"   href="/auth/login">${msg['user.login.href']}</a> -->
          [/#if]
          </div>

        ${content}
        </div>
        <div id="footer">
          <span class="copyright">2008-${currentDate?string("yyyy")}</span> ©
          <a class="home" href="http://${headers['Host']!"localhost"}">
          ${headers['Host']!"localhost"}
          </a>
        </div>
      </div>
  </body>
</html>
