<?xml version="1.0" encoding="iso-8859-1"?>

<!-- ======================================= -->
<!-- File    : REM_Default.xsl               -->
<!-- Content : Default REM stylesheet        -->
<!-- Author  : Amador Durán Toro             -->
<!-- Date    : 2004/09/16                    -->
<!-- Version : 1.2.2                         -->
<!-- ======================================= -->
<!-- Last update : 2002/08/19                -->
<!-- rem:changeRequestsStatusValue ->        -->
<!-- rem:changeRequestStatusValue            -->
<!-- ======================================= -->
<!-- Last update : 2002/07/18                -->
<!-- header_background variable defined and  -->
<!-- rem:name modified for setting 100%      -->
<!-- in second column                        -->
<!-- ======================================= -->
<!-- Last update : 2004/07/15                -->
<!-- rem:refs now use title attribute        -->
<!-- ======================================= -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- Global variables                              -->
<!-- ============================================= -->

<xsl:variable name="rem:header_background">#c8e0e0</xsl:variable>

<!-- ============================================= -->
<!-- Includes                                      -->
<!-- ============================================= -->

<xsl:include href="REM_SpecificationDocument.xsl"/>
    <xsl:include href="REM_Date.xsl"/>
    <xsl:include href="REM_Time.xsl"/>
    <xsl:include href="REM_TraceImage.xsl"/>
    <xsl:include href="REM_SpecificationObject.xsl"/>
        <xsl:include href="REM_Section.xsl"/>
        <xsl:include href="REM_Paragraph.xsl"/>
        <xsl:include href="REM_GlossaryItem.xsl"/>
        <xsl:include href="REM_GraphicFile.xsl"/>
        <xsl:include href="REM_Organization.xsl"/>
        <xsl:include href="REM_Stakeholder.xsl"/>
        <xsl:include href="REM_Meeting.xsl"/>
        <xsl:include href="REM_Actor.xsl"/>
        <xsl:include href="REM_C_Requirement.xsl"/>
            <xsl:include href="REM_Objective.xsl"/>
            <xsl:include href="REM_InformationRequirement.xsl"/>
            <xsl:include href="REM_ConstraintRequirement.xsl"/>
            <xsl:include href="REM_UseCase.xsl"/>
            <xsl:include href="REM_FunctionalRequirement.xsl"/>
            <xsl:include href="REM_NonFunctionalRequirement.xsl"/>
        <xsl:include href="REM_TraceabilityMatrix.xsl"/>
        <xsl:include href="REM_D_Requirement.xsl"/>
            <xsl:include href="REM_ObjectType.xsl"/>
            <xsl:include href="REM_ValueType.xsl"/>
            <xsl:include href="REM_AssociationType.xsl"/>
                <xsl:include href="REM_Attribute.xsl"/>
                <xsl:include href="REM_Invariant.xsl"/>
                <xsl:include href="REM_Role.xsl"/>
            <xsl:include href="REM_SystemOperation.xsl"/>
                <xsl:include href="REM_OperationException.xsl"/>
        <xsl:include href="REM_Conflict.xsl"/>
        <xsl:include href="REM_Defect.xsl"/>
            <xsl:include href="REM_Alternative.xsl"/>
        <xsl:include href="REM_ChangeRequest.xsl"/>
            <xsl:include href="REM_AffectedObjects.xsl"/>

<!-- ============================================= -->
<!-- Output method                                 -->
<!-- ============================================= -->

<xsl:output
    method="xml"
    omit-xml-declaration="yes"
    indent="yes"
/>

<!-- ============================================= -->
<!-- rem:text template                             -->
<!-- ============================================= -->

<xsl:template match="rem:text">
    <xsl:apply-templates/>
</xsl:template>

<!-- ============================================= -->
<!-- rem:ref template                              -->
<!-- ============================================= -->

<xsl:template match="rem:ref">
    <a>
        <xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
        <xsl:choose>
            <xsl:when test="//rem:glossaryItem[@oid=current()/@oid]">
                <xsl:for-each select="//rem:glossaryItem[@oid=current()/@oid]">
                    <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:text)"/></xsl:attribute>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="id(@oid)">
                    <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:name)"/></xsl:attribute>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:value-of select="."/>
    </a>
</xsl:template>

<!-- ============================================= -->
<!-- rem:tbd template                              -->
<!-- ============================================= -->

<xsl:template match="rem:tbd">
    <font color="red"><b><xsl:value-of select="$rem:lang_TBD"/></b></font>
</xsl:template>

<!-- ============================================= -->
<!-- br template                                   -->
<!-- ============================================= -->

<xsl:template match="br">
    <xsl:copy/>
</xsl:template>

<!-- ============================================== -->
<!-- Auxiliary templates                            -->
<!-- ============================================== -->

<!-- ============================================= -->
<!-- rem:name                                      -->
<!-- ============================================= -->

<!-- NOTE: anchors inside tables don't work in IE 5.5 -->

<xsl:template match="rem:name">
    <tr>
        <xsl:attribute name="bgcolor">
            <xsl:value-of select="$rem:header_background"/>
        </xsl:attribute>
        <th align="left" valign="top">
            <xsl:value-of select="../@oid"/>
        </th>
        <th align="left" valign="top" width="100%">
            <xsl:apply-templates/>
        </th>
    </tr>
</xsl:template>

<xsl:template match="
    ( rem:changeRequestStatusValue |
      rem:conflictStatusValue      |
      rem:defectStatusValue        |
      rem:defectTypeValue          |
      rem:importanceValue          |
      rem:stabilityValue           |
      rem:statusValue              |
      rem:terminationValue         |
      rem:timeIntervalValue        |
      rem:timeUnitValue            |
      rem:urgencyValue)/rem:name"
>
    <xsl:value-of select="."/>
</xsl:template>

<!-- ============================================= -->
<!-- GenerateRow                                   -->
<!-- ============================================= -->

<xsl:template name="GenerateRow">
    <xsl:param name="title"/>
    <xsl:param name="node" select="/.."/>
    <xsl:param name="span">1</xsl:param>
    <tr>
        <th align="left" valign="top"><xsl:value-of select="$title"/></th>
        <td align="left" valign="top">
            <xsl:attribute name="colspan"><xsl:value-of select="$span"/></xsl:attribute>
            <!-- <xsl:value-of select="$node"/> -->
            <xsl:apply-templates select="$node"/>
        </td>
    </tr>
</xsl:template>

<!-- ============================================= -->
<!-- GenerateAnchor                                -->
<!-- ============================================= -->

<xsl:template name="GenerateAnchor">
    <a><xsl:attribute name="name"><xsl:value-of select="@oid"/></xsl:attribute></a>
</xsl:template>

</xsl:stylesheet>
