import { defineConfig } from 'windicss/helpers'
import colors from 'windicss/colors'

const PRODUCTION = process.env.NODE_ENV !== 'production'

export default defineConfig({
  darkMode: 'class',
  mode: 'jit',
  extract: {
    include: ['**/*.{vue,html,jsx,tsx,scss,css}'],
    exclude: ['node_modules', '.git', 'dist', 'personal'],
  },
  theme: {
    extend: {
      transitionProperty: {
        spacing: 'margin, padding',
      },
      animation: {
        'spin-slow': 'spin 2.5s linear infinite',
      },
      fontFamily: {
        styled: ['Original Surfer', 'cursive'],
        poppins: ['Poppins', 'sans-serif'],
        'styled-code': ['Oxygen Mono', 'monospace'],
      },
      colors: {
        'warm-gray': colors.warmGray,
        'true-gray': colors.trueGray,
        emerald: colors.emerald,
        lime: colors.lime,
        amber: colors.amber,
        rose: colors.rose,
        primary: '#502427',
        'light-black': '#333333',
        'dark-black': '#1D1D1F',
        'cream-white': '#FAFAFA',
        'dirty-gray': '#B0B0B0',
        steel: {
          50: '#f9fafa',
          100: '#eff1f6',
          200: '#dddcec',
          300: '#b9b8d3',
          400: '#938fb4',
          500: '#766b95',
          600: '#5f5077',
          700: '#473c59',
          800: '#31283d',
          900: '#1c1825',
        },
        denim: {
          50: '#f9fafa',
          100: '#eef1f8',
          200: '#dadcf0',
          300: '#b5b8db',
          400: '#8f8fc0',
          500: '#736ba6',
          600: '#5c4f88',
          700: '#463b67',
          800: '#2f2846',
          900: '#1b182a',
        },
        sunset: {
          50: '#fff6f6',
          100: '#ffefef',
          200: '#f9d3d3',
          300: '#eaa6a6',
          400: '#e56d6d',
          500: '#d93c3e',
          600: '#bd0e0f',
          700: '#930705',
          800: '#680605',
          900: '#470201',
        },
      },
      borderRadius: {
        '15px': '15px',
      },
    },
  },
  shortcuts: {
    'debug-screens': PRODUCTION
      ? 'before:bottom-0 before:left-0 before:fixed before:z-[2147483647] before:px-1 before:text-12px before:bg-black before:text-white before:shadow-xl @sm:before:content-["screen:sm"] @md:before:content-["screen:md"] @lg:before:content-["screen:lg"] @xl:before:content-["screen:xl"] @2xl:before:content-["screen:2xl"] <sm:before:content-["screen:none"]'
      : '',
  },
  plugins: [require('windicss/plugin/line-clamp')],
})
