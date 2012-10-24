[#ftl]

[#assign content]
<h2>${msg['user.signup']}</h2>
<!--
<div class="user">
  [#if session['principal']??]
    <a href="/auth/logout">${msg['user.logout.href']}</a>
  ${session['principal'].login!}
  [#else]
    <a href="/auth/login">${msg['user.login.href']}</a>
  [/#if]
</div>
-->

<form class="form-horizontal"
      action="/auth/login"
      method="post">
  <div class="control-group">
    <label class="control-label" for="inputLogin">${msg['user.login']}</label>
    <div class="controls">
      <input type="text" id="inputLogin" name="log" placeholder="login">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputPassword">${msg['user.password']}</label>
    <div class="controls">
      <input type="password" id="inputPassword" name="pass" placeholder="Password">
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn">${msg['send']}</button>
    </div>
  </div>
</form>


[/#assign]

[#include "../layout.ftl"/]