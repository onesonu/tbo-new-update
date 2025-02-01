{
"status": {
"code": "${status.code! 'N/A'}",
"description": "${status.description! 'N/A'}"
},
<#if hotelResults?? && hotelResults?has_content>
"hotelResults": [
<#list hotelResults as hotelResult>
{
"hotelCode": "${hotelResult.hotelCode! 'N/A'}",
"currency": "${hotelResult.currency! 'N/A'}",
"rooms": [
<#list hotelResult.rooms as room>
{
"name": "${room.name! 'N/A'}",
"bookingCode": "${room.bookingCode! 'N/A'}",
"inclusion": "${room.inclusion! 'N/A'}",
"totalFare": ${room.totalFare! 0.0},
"totalTax": ${room.totalTax! 0.0},
"roomPromotion": "${room.roomPromotion! 'N/A'}",
"cancelPolicies": [
<#list room.cancelPolicies as policy>
{
"index": "${policy.index! 'N/A'}",
"fromDate": "${policy.fromDate! 'N/A'}",
"chargeType": "${policy.chargeType! 'N/A'}",
"cancellationCharge": ${policy.cancellationCharge! 0.0}
}<#if policy_has_next>,</#if>
</#list>
],
"mealType": "${room.mealType! 'N/A'}",
"isRefundable": ${room.isRefundable?c},
"withTransfers": ${room.withTransfers?c},
"dayRates": [
<#list room.dayRates as dayRateGroup>
<#list dayRateGroup as dayRate>
{
"basePrice": ${dayRate.basePrice! 0.0}
}<#if dayRate_has_next>,</#if>
</#list>
</#list>
],
"supplements": [
<#list room.supplements as supplementGroup>
<#list supplementGroup as supplement>
{
"index": ${supplement.index! 0},
"type": "${supplement.type! 'N/A'}",
"description": "${supplement.description! 'N/A'}",
"price": ${supplement.price! 0.0},
"currency": "${supplement.currency! 'N/A'}"
}<#if supplement_has_next>,</#if>
</#list>
</#list>
]
}<#if room_has_next>,</#if>
</#list>
]
}<#if hotelResult_has_next>,</#if>
</#list>
]
</#if>
}
