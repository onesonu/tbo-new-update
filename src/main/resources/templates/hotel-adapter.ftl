{
  "status": "${status! 'N/A'}",
  "error": <#if error??>
    {
      "errorCode": "${error.errorCode! 'N/A'}",
      "errorMessage": "${error.errorMessage! 'N/A'}"
    }
  </#if>,

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
                "dayRates": [
                  <#list room.dayRates as dayRate>
                    {
                      "basePrice": "${dayRate.basePrice! 0.0}"
                    }<#if dayRate_has_next>,</#if>
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

