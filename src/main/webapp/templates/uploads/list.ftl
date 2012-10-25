[#ftl]

[#assign content]
<h2>${msg['user.files.list']}</h2>
<table class="table table-striped">
  [#if Filess?size>0]
    [#list Filess as f]
   <tr>
     <td>
    ${f}
      </td>
    </tr>
    [/#list]
  [/#if]
 </table>
<a  class="btn" href="/book">${msg['user.button.back']!}</a>
[/#assign]

[#include "../layout.ftl"/]