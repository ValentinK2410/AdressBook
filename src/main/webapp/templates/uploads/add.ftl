[#ftl]

[#assign content]
<h2>${msg['user.files.add']}</h2>

<FORM ENCTYPE="multipart/form-data"
      ACTION="/book/${contact.id}/uploads"
      METHOD=POST>

  <INPUT NAME="file"
         TYPE="file">

  <INPUT TYPE="submit"
         VALUE="Send File">
  <input type="hidden"
         value="${currentDate?string("yyyyhhmmss")}"
         name="date">
</FORM>
<a class="btn"
   href="/book">
  <i class="icon-arrow-left">

  </i>${msg['user.button.back']!}</a>
[/#assign]

[#include "../layout.ftl"/]