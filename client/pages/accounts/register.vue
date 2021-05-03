<template>
  <div class="bg-cream-white">
    <div
      class="container flex items-center h-full px-6 mx-auto sm:px-0 place-content-center sm:place-content-evenly"
    >
      <form
        class="flex flex-col font-poppins font-light max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto space-y-5 md:text-xl sm:text-lg"
        @submit.prevent="submitForm"
      >
        <div
          class="flex items-center justify-center mt-10 mb-5 space-x-4 text-3xl font-bold text-center sm:text-4xl sm:mb-10 font-poppins text-primary"
        >
          <div>Create your</div>
          <img
            :src="require('~/assets/images/logo.png')"
            alt="Resourceium logo"
            class="h-10 sm:h-14"
            rel="preload"
            align="middle"
          />
          <div>ID.</div>
        </div>
        <NuxtLink
          :to="{ name: 'accounts-login' }"
          class="text-xs text-center hover:underline"
        >
          <div>Already have a Resourceium account?</div>
          <div>Find it here.</div>
        </NuxtLink>
        <base-input-box
          v-model="formData.username"
          name="username"
          placeholder-text="Username"
        ></base-input-box>
        <div class="flex flex-wrap items-center space-y-1 sm:space-y-0">
          <div class="w-full sm:pr-2 sm:w-1/2">
            <base-input-box
              v-model="formData.firstName"
              name="first-name"
              placeholder-text="First Name"
            ></base-input-box>
          </div>
          <div class="w-full sm:pl-2 sm:w-1/2">
            <base-input-box
              v-model="formData.lastName"
              name="last-name"
              placeholder-text="Last Name"
            ></base-input-box>
          </div>
        </div>
        <base-select-box
          v-model="formData.country"
          name="country"
          :select-options="getCountryList"
          label="Country/Region"
        ></base-select-box>
        <base-input-box
          v-model="formData.dateOfBirth"
          name="date-of-birth"
          placeholder-text="Date of Birth"
          input-type="date"
          no-hide-label
        ></base-input-box>
        <base-input-box
          v-model="formData.email"
          name="email"
          placeholder-text="Email"
          input-type="email"
        ></base-input-box>
        <div
          class="flex flex-wrap items-center space-y-1 text-base sm:space-y-0"
        >
          <div class="w-full sm:pr-2 sm:w-1/2">
            <base-input-box
              v-model="formData.password"
              name="password"
              placeholder-text="Password"
              input-type="password"
            ></base-input-box>
          </div>
          <div class="w-full sm:pl-2 sm:w-1/2">
            <base-input-box
              v-model="formData.password2"
              name="password2"
              placeholder-text="Confirm password"
              input-type="password"
            ></base-input-box>
          </div>
        </div>
        <button
          type="submit"
          class="flex justify-center py-2 space-x-3 transition duration-300 transform rounded-15px hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
        >
          <div class="text-white">Register</div>
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { countries } from 'countries-list'

export default {
  data() {
    return {
      formData: {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        dateOfBirth: '',
        country: '',
        password: '',
        password2: '',
      },
    }
  },
  head: () => ({ title: 'Register' }),
  computed: {
    getCountryList() {
      const countryList = []
      for (const country in countries) {
        countryList.push(countries[country].name)
      }
      countryList.sort((val1, val2) => val1.localeCompare(val2))
      return countryList
    },
  },
  methods: {
    submitForm() {
      const formData = this.formData
      if (formData.password !== formData.password2) {
        alert('The two passwords do not match')
        return
      }
      this.$axios
        .$post('/accounts/register', null, { params: formData })
        .then((_result) => {
          this.$addAlert({
            message: 'Please login with your new account now',
            type: 'success',
          })
          this.$router.push({ name: 'accounts-login' })
        })
        .catch((err) => {
          let message = null
          if (typeof err.response.data === 'object') {
            message = err.response.data.message
          } else {
            message = JSON.parse(err.response.data.message)
            message = message.join('\n')
          }
          this.$addAlert({
            message,
            type: 'danger',
          })
        })
    },
  },
}
</script>
