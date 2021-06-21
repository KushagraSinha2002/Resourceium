<template>
  <div class="bg-cream-white">
    <div
      class="container flex items-center px-6 py-12 mx-auto md:py-0 md:h-full sm:px-0 place-content-center sm:place-content-evenly"
    >
      <form
        class="flex flex-col font-poppins font-light max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto space-y-5 md:text-xl sm:text-lg"
        @submit.prevent="submitForm"
      >
        <div
          class="flex items-center justify-center mb-2 text-3xl font-bold text-center sm:text-4xl sm:mb-5 font-poppins text-primary"
        >
          Change your Resourceium password
        </div>
        <NuxtLink
          id="link-login"
          :to="{ name: 'accounts-login' }"
          class="text-xs text-center hover:underline"
        >
          <div>Already have a Resourceium account?</div>
          <div>Login here.</div>
        </NuxtLink>
        <div>
          <base-input-box
            v-model="formData.username"
            name="username"
            placeholder-text="Username"
            focus-on-render
          ></base-input-box>
          <div v-if="usernameValidator" class="font-serif text-sm text-red-500">
            Username can contain only alphabets and digits.
          </div>
        </div>
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
        <base-loading-button
          type="submit"
          text="Change"
          :loading="loading"
          :disabled="usernameValidator"
        ></base-loading-button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      formData: {
        username: '',
        email: '',
        password: '',
        password2: '',
      },
    }
  },
  head: () => ({ title: 'Change password' }),
  computed: {
    usernameValidator() {
      const nameRegex = /^[a-zA-Z0-9]+$/
      return !nameRegex.test(this.formData.username)
    },
  },
  methods: {
    async submitForm() {
      const formData = this.formData
      if (formData.password !== formData.password2) {
        alert('The two passwords do not match')
        return
      }
      this.loading = true
      await this.$axios
        .$post('/accounts/change-password', null, { params: formData })
        .then((_result) => {
          this.$addAlert({
            message: 'Password changed, login with your new password now',
            type: 'success',
          })
          this.$router.push({ name: 'accounts-login' })
        })
        .catch((err) => {
          this.$addAlert({
            message: err.response.data,
            type: 'danger',
          })
        })
      this.loading = false
    },
  },
}
</script>
