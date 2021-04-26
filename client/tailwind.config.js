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
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [require('tailwindcss-debug-screens')],
}
