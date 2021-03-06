<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Thymeleaf HTML Template Example</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 48px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
    <tr>
        <td align="center" bgcolor="#eaeaea" style="padding: 40px 0 30px 0;">
            <p>Monster</p>
        </td>
    </tr>
    <tr>
        <td bgcolor="#eaeaea" style="padding: 40px 30px 40px 30px;">
            <p>${'Dear ' + customerName }</p>
            <div>
                <p>You've requested about order.</p>
                <#list products as product>
                    ${product.previewImage}: ${product.name}: ${product.price}
                </#list>
            </div>
            <div>
                <p>${'Delivery: ' + delivery }</p>
                <p>${'Payment: ' + payment }</p>
                <p>${'Customer: ' + customer }</p>
                <p>${'Recipient: ' + recipient }</p>
                <p>${'Total amount: ' + amount }</p>
            </div>
            <p>Thanks</p>
        </td>
    </tr>
</table>

</body>
</html>