const express = require('express');
const stripe = require('stripe')('{{ INSERT STRIPE SECRET KEY }}');

const app = express();
app.use(express.static('public'));
app.post('/payments', async (req, res) => {
  const { client_secret } = await stripe.paymentIntents.create({
    amount: 2500,
    currency: 'usd',
    payment_method_types: ['card'],
  });
  res.send(JSON.stringify({ clientSecret: client_secret }));
});

app.listen(8080, () => console.log('Application started'));
