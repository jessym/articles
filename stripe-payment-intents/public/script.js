const stripe = Stripe('{{ INSERT STRIPE PUBLISHABLE KEY }}');
const elements = stripe.elements();

const cardElement = elements.create('card');
cardElement.mount('#card-element');

function initializePayment() {
  return fetch('/payments', { method: 'POST' })
    .then(res => res.text())
    .then(JSON.parse);
}

async function confirmPayment(clientSecret) {
  const result = await stripe.confirmCardPayment(clientSecret, {
    payment_method: {
      card: cardElement,
    },
  });
  if (result.error) {
    console.error(result.error);
  } else {
    alert('Thank you for your business!');
  }
}

document.getElementById('pay-button')
  .addEventListener('click', async () => {
    const { clientSecret } = await initializePayment();
    confirmPayment(clientSecret);
  });
