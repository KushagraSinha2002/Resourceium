<template>
  <div class="flex items-center justify-center min-h-screen">
    <form class="flex flex-col max-w-sm space-y-5" @submit.prevent="submitForm">
      <base-input-box v-model="formData.name" name="username"></base-input-box>
      <base-input-box
        v-model="formData.email"
        name="email"
        input-type="email"
      ></base-input-box>
      <base-input-box
        v-model="formData.password"
        name="password"
        input-type="password"
      ></base-input-box>
      <base-input-box
        v-model="formData.password2"
        name="confirm password"
        input-type="password"
      ></base-input-box>
      <button
        type="submit"
        class="flex justify-center py-2 space-x-3 rounded-lg focus:outline-none bg-lime-600 ring-blue-200 ring"
      >
        <ig-icon name="check" no-color class="text-black"></ig-icon>
        <div>Submit</div>
      </button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        name: '',
        email: '',
        password: '',
        password2: '',
      },
    }
  },
  head: () => ({ title: 'Register' }),
  methods: {
    submitForm() {
      const formData = this.formData
      if (formData.password !== formData.password2) {
        alert('The two passwords do not match')
      }
      this.$axios
        .$post('/accounts/register', null, { params: formData })
        .then((_result) => {
          this.$addAlert({
            severity: 'info',
            messageHeading: 'Registration successful',
            messageBody: 'Please login with your new account now',
            active: true,
          })
          this.$router.push({ name: 'accounts-login' })
        })
        .catch((err) => {
          this.$addAlert({
            severity: 'warning',
            messageHeading: 'Invalid input',
            messageBody: err.response.data.message,
            active: true,
          })
        })
    },
  },
}
</script>