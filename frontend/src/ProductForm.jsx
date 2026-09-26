import { useState } from 'react'
// import './App.css'
import { createProduct } from './api.js'

function ProductForm() {
  const [product, setProduct] = useState("");
  const [message, setMessage] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    try {
        await createProduct(product);
        setProduct("");
        setMessage("Created");
    } catch (err) {
        setMessage(err.message);
    }
  }

  return (
    <form onSubmit={handleSubmit}>
        <input
            value={product}
            onChange={(e) => setProduct(e.target.value)}
        />
        <button type="submit">Add</button>
        {message && <p>{message}</p>}
    </form>

  );
}

export default ProductForm
