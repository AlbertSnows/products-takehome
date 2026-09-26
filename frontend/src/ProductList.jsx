import { useState, useEffect } from 'react'
// import './App.css'
import { fetchProducts } from './api';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  async function loadProducts() {
    try {
        const data = await fetchProducts();
        setProducts(data);
        setError(null);
    } catch (err) {
        setError(err.message);
    }
  }

  useEffect(() => {
    loadProducts();
  }, []);

  return (
    <div>
        <button onClick={loadProducts}>Refresh</button>
        {error && <p>{error}</p>}
        <ul>
            {products.map((product) => (
                <li key={product.id}>{product.name}</li>
            ))}
        </ul>

    </div>
  );
}

export default ProductList
