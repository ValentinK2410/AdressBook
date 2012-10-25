[#ftl]

[#assign content]
<h2>${msg['user.new.user']}</h2>
<form action="/auth"
      method="post">
  <p>
    <label for="l">${msg['user.login']}</label>
    <input id="l"
           type="text"
           name="log">
  </p>
  <p>
    <label for="p">${msg['user.password']}</label>
    <input id="p"
           type="password"
           name="pass">
  </p>
  <p>
    <label for="m">${msg['user.email']}</label>
    <input id="m"
           type="text"
           name="mail">
  </p>

  <input type="submit"
         value="${msg['user.button.send']}">
</form>
[/#assign]

[#include "layout.ftl"/]