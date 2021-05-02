const ignisnents = require('ignisnents/dist/tailwind-preset.js')

module.exports = {
  darkMode: 'class', // or 'media' or 'class'
  mode: 'jit',
  presets: [ignisnents],
  purge: [
    'components/**/*.vue',
    'layouts/**/*.vue',
    'pages/**/*.vue',
    'plugins/**/*.js',
    'nuxt.config.js',
    'node_modules/ignisnents/src/components/**/*.vue',
  ],
  theme: {
    extend: {
      fontFamily: {
        styled: ['Original Surfer', 'cursive'],
        poppins: ['Poppins', 'sans-serif'],
        'styled-code': ['Oxygen Mono', 'monospace'],
      },
      colors: {
        primary: '#502427',
        'light-black': '#333333',
        'dark-black': '#1D1D1F',
        'cream-white': '#FAFAFA',
      },
      borderRadius: {
        '15px': '15px',
      },
    },
  },
  variants: {
    extend: {},
  },
  plugins: [require('tailwindcss-debug-screens')],
}
