[#ftl]



[#assign content]

<h3>${msg['user.new.user']!}</h3>

<form class="form-horizontal"
      action="/auth/signup"
      method="post">
  <div class="control-group">
    <label class="control-label" for="inputLogin">${msg['user.login']!}</label>
    <div class="controls">
      <input type="text" id="inputLogin" name="log" placeholder="login">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputPassword">${msg['user.password']!}</label>
    <div class="controls">
      <input type="password" id="inputPassword" name="pass" placeholder="Password">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputOld-pass">${msg['user.password.repeat']!}</label>
    <div class="controls">
      <input type="password" id="inputOld-pass" name="old-pass" placeholder="Old password">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputEmail">${msg['user.email']!}</label>
    <div class="controls">
      <input type="text" id="inputEmail" name="email" placeholder="Email">
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