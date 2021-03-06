<#ftl encoding="ISO-8859-1">
<#-- @ftlvariable name="branches" type="org.berlinvegan.generators.model.GastroLocation[]" -->
<#-- @ftlvariable name="language" type="java.lang.String" -->

<#assign branch1 = branches[0]>
<script type='text/javascript'>var disqus_identifier = '${branch1.reviewURL}';</script>

<h4 class='address'>Adresse</h4>
<#list branches as branch>
<p>${branch.street}<br/>${branch.cityCode?c} ${branch.district}
    <#if branch.publicTransport??>
        <br/>${branch.publicTransport}
    </#if>
</p>
    <#if branch.telephone??>
    <p>Tel.: ${branch.telephone}</p>
    </#if>

</#list>
<#if branch1.website??>
<h4 class="internet">Internet</h4>
<p><a target='_blank' href='${branch1.website}'>${branch1.website}</a></p>
</#if>

<h4 class="openingTime">�ffnungszeiten</h4>
${branch1.getOpenTimesHTML("de")}
<#if branch1.openComment??>
<p>${branch1.openComment}</p>
</#if>
<#if branch1.vegan == 5 || branch1.dog != -1 || branch1.organic == 1 || branch1.glutenFree == 1 || branch1.handicappedAccessible != -1
|| branch1.childChair != -1 || branch1.catering == 1 || branch1.delivery == 1
|| branch1.seatsIndoor != -1 || branch1.seatsOutdoor != -1 || branch1.wlan == 1>
<h4 class="misc">Sonstiges</h4>
<ul>
    <li>${branch1.getVeganHTML("de")}</li>
    <#if branch1.organic == 1>
        <li> Bio</li>
    </#if>
    <#if branch1.glutenFree == 1>
        <li> glutenfreie Speisen</li>
    </#if>

    <#if branch1.handicappedAccessible != -1>
        <#if branch1.handicappedAccessible == 1>
            <li> Rollstuhl geeignet</li>
            <#if branch1.handicappedAccessibleWc == 1>
                <li> WC Rollstuhl geeignet</li>
            <#else>
                <li> WC Rollstuhl ungeeignet</li>
            </#if>
        <#else>
            <li> Rollstuhl ungeeignet</li>
        </#if>

    </#if>

    <#if branch1.dog != -1>
        <#if branch1.dog == 1>
            <li> Hunde erlaubt</li>
        <#else>
            <li> Hunde nicht erlaubt</li>
        </#if>
    </#if>
    <#if branch1.childChair != -1>
        <#if branch1.childChair == 1>
            <li> Kindersitz vorhanden</li>
        <#else>
            <li> kein Kindersitz vorhanden</li>
        </#if>
    </#if>
    <#if branch1.wlan == 1>
        <li> WLAN vorhanden</li>
    </#if>
    <#if branch1.catering == 1>
        <li> Catering</li>
    </#if>
    <#if branch1.delivery == 1>
        <li> Lieferservice</li>
    </#if>
    <#if branch1.seatsIndoor != -1>
        <li> Sitzpl�tze innen: ${branch1.seatsIndoor}</li>
    </#if>
    <#if branch1.seatsOutdoor != -1>
        <li> Sitzpl�tze au�en: ${branch1.seatsOutdoor}</li>
    </#if>
    <#if branch1.seatsOutdoor != -1>
    </#if>
</ul>
</#if>


<script type="text/javascript">
    var textDiv = document.getElementById('site-content');
    var disqusDiv = document.createElement('div');
    disqusDiv.setAttribute('id', "disqus_thread");
    textDiv.appendChild(disqusDiv);
</script>
<!--<div id="disqus_thread"></div>-->

<style type="text/css">
    #dsq-content ul li {
        background: none;
        padding-left: 0px;
    }

    #dsq-content .dsq-options {
        font-size: 10px;
    }

    #dsq-content .dsq-pagination {
        display: none;
    }

    #dsq-content #dsq-footer {
        margin: 0;
        font-size: 11px;
    }

    #dsq-comment-footer {
        font-size: 9px;
    }
</style>
<script type="text/javascript">
    /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
    var disqus_shortname = 'berlin-vegan'; // required: replace example with your forum shortname

    /* * * DON'T EDIT BELOW THIS LINE * * */
    (function () {
        var dsq = document.createElement('script');
        dsq.type = 'text/javascript';
        dsq.async = true;
        dsq.src = 'https://' + disqus_shortname + '.disqus.com/embed.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
    })();
</script>
<noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>