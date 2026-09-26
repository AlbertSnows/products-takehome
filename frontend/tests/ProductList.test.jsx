import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { vi } from 'vitest'
import ProductList from '../src/ProductList.jsx'
import { fetchProducts } from '../src/api.js'

vi.mock('../src/api.js')

test('shows the products it loads', async () => {
    fetchProducts.mockResolvedValue([{ id: '1', name: 'foo' }]);
    render(<ProductList />);
    const input = await screen.findByText('foo');
    expect(input).toBeInTheDocument();
})

test('shows an error when the fetch fails', async () => {
    fetchProducts.mockRejectedValue(new Error('Request failed: 500'));
    render(<ProductList />);
    const input = await screen.findByText('Request failed: 500');
    expect(input).toBeInTheDocument();
})

test('loads again when Refresh is clicked', async () => {
    fetchProducts.mockResolvedValueOnce([{ id: '1', name: 'foo' }]);
    fetchProducts.mockResolvedValueOnce([{ id: '1', name: 'foo' }, { id: '2', name: 'bar'}]);
    render(<ProductList />);
    expect(await screen.findByText('foo')).toBeInTheDocument();
    expect(screen.queryByText('bar')).not.toBeInTheDocument();

    await userEvent.click(screen.getByRole('button', { name: 'Refresh' }));
    expect(await screen.findByText('bar')).toBeInTheDocument();
})
