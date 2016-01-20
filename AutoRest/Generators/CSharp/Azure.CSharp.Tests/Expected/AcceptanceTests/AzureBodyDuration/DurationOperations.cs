// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator 0.14.0.0
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

namespace Fixtures.Azure.AcceptanceTestsAzureBodyDuration
{
    using System;
    using System.Linq;
    using System.Collections.Generic;
    using System.Net;
    using System.Net.Http;
    using System.Net.Http.Headers;
    using System.Text;
    using System.Text.RegularExpressions;
    using System.Threading;
    using System.Threading.Tasks;
    using Microsoft.Rest;
    using Microsoft.Rest.Serialization;
    using Newtonsoft.Json;
    using Microsoft.Rest.Azure;
    using Models;

    /// <summary>
    /// DurationOperations operations.
    /// </summary>
    internal partial class DurationOperations : IServiceOperations<AutoRestDurationTestService>, IDurationOperations
    {
        /// <summary>
        /// Initializes a new instance of the DurationOperations class.
        /// </summary>
        /// <param name='client'>
        /// Reference to the service client.
        /// </param>
        internal DurationOperations(AutoRestDurationTestService client)
        {
            if (client == null) 
            {
                throw new ArgumentNullException("client");
            }
            this.Client = client;
        }

        /// <summary>
        /// Gets a reference to the AutoRestDurationTestService
        /// </summary>
        public AutoRestDurationTestService Client { get; private set; }

        /// <summary>
        /// Get null duration value
        /// </summary>
        /// <param name='customHeaders'>
        /// Headers that will be added to request.
        /// </param>
        /// <param name='cancellationToken'>
        /// The cancellation token.
        /// </param>
        public async Task<AzureOperationResponse<TimeSpan?>> GetNullWithHttpMessagesAsync(Dictionary<string, List<string>> customHeaders = null, CancellationToken cancellationToken = default(CancellationToken))
        {
            // Tracing
            bool _shouldTrace = ServiceClientTracing.IsEnabled;
            string _invocationId = null;
            if (_shouldTrace)
            {
                _invocationId = ServiceClientTracing.NextInvocationId.ToString();
                Dictionary<string, object> tracingParameters = new Dictionary<string, object>();
                tracingParameters.Add("cancellationToken", cancellationToken);
                ServiceClientTracing.Enter(_invocationId, this, "GetNull", tracingParameters);
            }
            // Construct URL
            var _baseUrl = this.Client.BaseUri.AbsoluteUri;
            var _url = new Uri(new Uri(_baseUrl + (_baseUrl.EndsWith("/") ? "" : "/")), "duration/null").ToString();
            List<string> _queryParameters = new List<string>();
            if (_queryParameters.Count > 0)
            {
                _url += "?" + string.Join("&", _queryParameters);
            }
            // Create HTTP transport objects
            HttpRequestMessage _httpRequest = new HttpRequestMessage();
            HttpResponseMessage _httpResponse = null;
            try
            {
                _httpRequest.Method = new HttpMethod("GET");
                _httpRequest.RequestUri = new Uri(_url);
                // Set Headers
                if (this.Client.GenerateClientRequestId != null && this.Client.GenerateClientRequestId.Value)
                {
                    _httpRequest.Headers.TryAddWithoutValidation("x-ms-client-request-id", Guid.NewGuid().ToString());
                }
                if (this.Client.AcceptLanguage != null)
                {
                    if (_httpRequest.Headers.Contains("accept-language"))
                    {
                        _httpRequest.Headers.Remove("accept-language");
                    }
                    _httpRequest.Headers.TryAddWithoutValidation("accept-language", this.Client.AcceptLanguage);
                }
                if (customHeaders != null)
                {
                    foreach(var _header in customHeaders)
                    {
                        if (_httpRequest.Headers.Contains(_header.Key))
                        {
                            _httpRequest.Headers.Remove(_header.Key);
                        }
                        _httpRequest.Headers.TryAddWithoutValidation(_header.Key, _header.Value);
                    }
                }

                // Serialize Request
                string _requestContent = null;
                // Set Credentials
                if (this.Client.Credentials != null)
                {
                    cancellationToken.ThrowIfCancellationRequested();
                    await this.Client.Credentials.ProcessHttpRequestAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                }
                // Send Request
                if (_shouldTrace)
                {
                    ServiceClientTracing.SendRequest(_invocationId, _httpRequest);
                }
                cancellationToken.ThrowIfCancellationRequested();
                _httpResponse = await this.Client.HttpClient.SendAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                if (_shouldTrace)
                {
                    ServiceClientTracing.ReceiveResponse(_invocationId, _httpResponse);
                }
                HttpStatusCode _statusCode = _httpResponse.StatusCode;
                cancellationToken.ThrowIfCancellationRequested();
                string _responseContent = null;
                if ((int)_statusCode != 200)
                {
                    var ex = new ErrorException(string.Format("Operation returned an invalid status code '{0}'", _statusCode));
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        Error _errorBody = SafeJsonConvert.DeserializeObject<Error>(_responseContent, this.Client.DeserializationSettings);
                        if (_errorBody != null)
                        {
                            ex.Body = _errorBody;
                        }
                    }
                    catch (JsonException)
                    {
                        // Ignore the exception
                    }
                    ex.Request = new HttpRequestMessageWrapper(_httpRequest, _requestContent);
                    ex.Response = new HttpResponseMessageWrapper(_httpResponse, _responseContent);
                    if (_shouldTrace)
                    {
                        ServiceClientTracing.Error(_invocationId, ex);
                    }
                    throw ex;
                }
                // Create Result
                var _result = new AzureOperationResponse<TimeSpan?>();
                _result.Request = _httpRequest;
                _result.Response = _httpResponse;
                if (_httpResponse.Headers.Contains("x-ms-request-id"))
                {
                    _result.RequestId = _httpResponse.Headers.GetValues("x-ms-request-id").FirstOrDefault();
                }
                // Deserialize Response
                if ((int)_statusCode == 200)
                {
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        _result.Body = SafeJsonConvert.DeserializeObject<TimeSpan?>(_responseContent, this.Client.DeserializationSettings);
                    }
                    catch (JsonException ex)
                    {
                        throw new RestException("Unable to deserialize the response.", ex);
                    }
                }
                if (_shouldTrace)
                {
                    ServiceClientTracing.Exit(_invocationId, _result);
                }
                return _result;
            }
            catch
            {
                _httpRequest.Dispose();
                if (_httpResponse != null)
                {
                    _httpResponse.Dispose();
                }
                throw;
            }    
        }

        /// <summary>
        /// Put a positive duration value
        /// </summary>
        /// <param name='durationBody'>
        /// </param>
        /// <param name='customHeaders'>
        /// Headers that will be added to request.
        /// </param>
        /// <param name='cancellationToken'>
        /// The cancellation token.
        /// </param>
        public async Task<AzureOperationResponse> PutPositiveDurationWithHttpMessagesAsync(TimeSpan? durationBody, Dictionary<string, List<string>> customHeaders = null, CancellationToken cancellationToken = default(CancellationToken))
        {
            if (durationBody == null)
            {
                throw new ValidationException(ValidationRules.CannotBeNull, "durationBody");
            }
            // Tracing
            bool _shouldTrace = ServiceClientTracing.IsEnabled;
            string _invocationId = null;
            if (_shouldTrace)
            {
                _invocationId = ServiceClientTracing.NextInvocationId.ToString();
                Dictionary<string, object> tracingParameters = new Dictionary<string, object>();
                tracingParameters.Add("durationBody", durationBody);
                tracingParameters.Add("cancellationToken", cancellationToken);
                ServiceClientTracing.Enter(_invocationId, this, "PutPositiveDuration", tracingParameters);
            }
            // Construct URL
            var _baseUrl = this.Client.BaseUri.AbsoluteUri;
            var _url = new Uri(new Uri(_baseUrl + (_baseUrl.EndsWith("/") ? "" : "/")), "duration/positiveduration").ToString();
            List<string> _queryParameters = new List<string>();
            if (_queryParameters.Count > 0)
            {
                _url += "?" + string.Join("&", _queryParameters);
            }
            // Create HTTP transport objects
            HttpRequestMessage _httpRequest = new HttpRequestMessage();
            HttpResponseMessage _httpResponse = null;
            try
            {
                _httpRequest.Method = new HttpMethod("PUT");
                _httpRequest.RequestUri = new Uri(_url);
                // Set Headers
                if (this.Client.GenerateClientRequestId != null && this.Client.GenerateClientRequestId.Value)
                {
                    _httpRequest.Headers.TryAddWithoutValidation("x-ms-client-request-id", Guid.NewGuid().ToString());
                }
                if (this.Client.AcceptLanguage != null)
                {
                    if (_httpRequest.Headers.Contains("accept-language"))
                    {
                        _httpRequest.Headers.Remove("accept-language");
                    }
                    _httpRequest.Headers.TryAddWithoutValidation("accept-language", this.Client.AcceptLanguage);
                }
                if (customHeaders != null)
                {
                    foreach(var _header in customHeaders)
                    {
                        if (_httpRequest.Headers.Contains(_header.Key))
                        {
                            _httpRequest.Headers.Remove(_header.Key);
                        }
                        _httpRequest.Headers.TryAddWithoutValidation(_header.Key, _header.Value);
                    }
                }

                // Serialize Request
                string _requestContent = null;
                _requestContent = SafeJsonConvert.SerializeObject(durationBody, this.Client.SerializationSettings);
                _httpRequest.Content = new StringContent(_requestContent, Encoding.UTF8);
                _httpRequest.Content.Headers.ContentType = MediaTypeHeaderValue.Parse("application/json; charset=utf-8");
                // Set Credentials
                if (this.Client.Credentials != null)
                {
                    cancellationToken.ThrowIfCancellationRequested();
                    await this.Client.Credentials.ProcessHttpRequestAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                }
                // Send Request
                if (_shouldTrace)
                {
                    ServiceClientTracing.SendRequest(_invocationId, _httpRequest);
                }
                cancellationToken.ThrowIfCancellationRequested();
                _httpResponse = await this.Client.HttpClient.SendAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                if (_shouldTrace)
                {
                    ServiceClientTracing.ReceiveResponse(_invocationId, _httpResponse);
                }
                HttpStatusCode _statusCode = _httpResponse.StatusCode;
                cancellationToken.ThrowIfCancellationRequested();
                string _responseContent = null;
                if ((int)_statusCode != 200)
                {
                    var ex = new ErrorException(string.Format("Operation returned an invalid status code '{0}'", _statusCode));
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        Error _errorBody = SafeJsonConvert.DeserializeObject<Error>(_responseContent, this.Client.DeserializationSettings);
                        if (_errorBody != null)
                        {
                            ex.Body = _errorBody;
                        }
                    }
                    catch (JsonException)
                    {
                        // Ignore the exception
                    }
                    ex.Request = new HttpRequestMessageWrapper(_httpRequest, _requestContent);
                    ex.Response = new HttpResponseMessageWrapper(_httpResponse, _responseContent);
                    if (_shouldTrace)
                    {
                        ServiceClientTracing.Error(_invocationId, ex);
                    }
                    throw ex;
                }
                // Create Result
                var _result = new AzureOperationResponse();
                _result.Request = _httpRequest;
                _result.Response = _httpResponse;
                if (_httpResponse.Headers.Contains("x-ms-request-id"))
                {
                    _result.RequestId = _httpResponse.Headers.GetValues("x-ms-request-id").FirstOrDefault();
                }
                if (_shouldTrace)
                {
                    ServiceClientTracing.Exit(_invocationId, _result);
                }
                return _result;
            }
            catch
            {
                _httpRequest.Dispose();
                if (_httpResponse != null)
                {
                    _httpResponse.Dispose();
                }
                throw;
            }    
        }

        /// <summary>
        /// Get a positive duration value
        /// </summary>
        /// <param name='customHeaders'>
        /// Headers that will be added to request.
        /// </param>
        /// <param name='cancellationToken'>
        /// The cancellation token.
        /// </param>
        public async Task<AzureOperationResponse<TimeSpan?>> GetPositiveDurationWithHttpMessagesAsync(Dictionary<string, List<string>> customHeaders = null, CancellationToken cancellationToken = default(CancellationToken))
        {
            // Tracing
            bool _shouldTrace = ServiceClientTracing.IsEnabled;
            string _invocationId = null;
            if (_shouldTrace)
            {
                _invocationId = ServiceClientTracing.NextInvocationId.ToString();
                Dictionary<string, object> tracingParameters = new Dictionary<string, object>();
                tracingParameters.Add("cancellationToken", cancellationToken);
                ServiceClientTracing.Enter(_invocationId, this, "GetPositiveDuration", tracingParameters);
            }
            // Construct URL
            var _baseUrl = this.Client.BaseUri.AbsoluteUri;
            var _url = new Uri(new Uri(_baseUrl + (_baseUrl.EndsWith("/") ? "" : "/")), "duration/positiveduration").ToString();
            List<string> _queryParameters = new List<string>();
            if (_queryParameters.Count > 0)
            {
                _url += "?" + string.Join("&", _queryParameters);
            }
            // Create HTTP transport objects
            HttpRequestMessage _httpRequest = new HttpRequestMessage();
            HttpResponseMessage _httpResponse = null;
            try
            {
                _httpRequest.Method = new HttpMethod("GET");
                _httpRequest.RequestUri = new Uri(_url);
                // Set Headers
                if (this.Client.GenerateClientRequestId != null && this.Client.GenerateClientRequestId.Value)
                {
                    _httpRequest.Headers.TryAddWithoutValidation("x-ms-client-request-id", Guid.NewGuid().ToString());
                }
                if (this.Client.AcceptLanguage != null)
                {
                    if (_httpRequest.Headers.Contains("accept-language"))
                    {
                        _httpRequest.Headers.Remove("accept-language");
                    }
                    _httpRequest.Headers.TryAddWithoutValidation("accept-language", this.Client.AcceptLanguage);
                }
                if (customHeaders != null)
                {
                    foreach(var _header in customHeaders)
                    {
                        if (_httpRequest.Headers.Contains(_header.Key))
                        {
                            _httpRequest.Headers.Remove(_header.Key);
                        }
                        _httpRequest.Headers.TryAddWithoutValidation(_header.Key, _header.Value);
                    }
                }

                // Serialize Request
                string _requestContent = null;
                // Set Credentials
                if (this.Client.Credentials != null)
                {
                    cancellationToken.ThrowIfCancellationRequested();
                    await this.Client.Credentials.ProcessHttpRequestAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                }
                // Send Request
                if (_shouldTrace)
                {
                    ServiceClientTracing.SendRequest(_invocationId, _httpRequest);
                }
                cancellationToken.ThrowIfCancellationRequested();
                _httpResponse = await this.Client.HttpClient.SendAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                if (_shouldTrace)
                {
                    ServiceClientTracing.ReceiveResponse(_invocationId, _httpResponse);
                }
                HttpStatusCode _statusCode = _httpResponse.StatusCode;
                cancellationToken.ThrowIfCancellationRequested();
                string _responseContent = null;
                if ((int)_statusCode != 200)
                {
                    var ex = new ErrorException(string.Format("Operation returned an invalid status code '{0}'", _statusCode));
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        Error _errorBody = SafeJsonConvert.DeserializeObject<Error>(_responseContent, this.Client.DeserializationSettings);
                        if (_errorBody != null)
                        {
                            ex.Body = _errorBody;
                        }
                    }
                    catch (JsonException)
                    {
                        // Ignore the exception
                    }
                    ex.Request = new HttpRequestMessageWrapper(_httpRequest, _requestContent);
                    ex.Response = new HttpResponseMessageWrapper(_httpResponse, _responseContent);
                    if (_shouldTrace)
                    {
                        ServiceClientTracing.Error(_invocationId, ex);
                    }
                    throw ex;
                }
                // Create Result
                var _result = new AzureOperationResponse<TimeSpan?>();
                _result.Request = _httpRequest;
                _result.Response = _httpResponse;
                if (_httpResponse.Headers.Contains("x-ms-request-id"))
                {
                    _result.RequestId = _httpResponse.Headers.GetValues("x-ms-request-id").FirstOrDefault();
                }
                // Deserialize Response
                if ((int)_statusCode == 200)
                {
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        _result.Body = SafeJsonConvert.DeserializeObject<TimeSpan?>(_responseContent, this.Client.DeserializationSettings);
                    }
                    catch (JsonException ex)
                    {
                        throw new RestException("Unable to deserialize the response.", ex);
                    }
                }
                if (_shouldTrace)
                {
                    ServiceClientTracing.Exit(_invocationId, _result);
                }
                return _result;
            }
            catch
            {
                _httpRequest.Dispose();
                if (_httpResponse != null)
                {
                    _httpResponse.Dispose();
                }
                throw;
            }    
        }

        /// <summary>
        /// Get an invalid duration value
        /// </summary>
        /// <param name='customHeaders'>
        /// Headers that will be added to request.
        /// </param>
        /// <param name='cancellationToken'>
        /// The cancellation token.
        /// </param>
        public async Task<AzureOperationResponse<TimeSpan?>> GetInvalidWithHttpMessagesAsync(Dictionary<string, List<string>> customHeaders = null, CancellationToken cancellationToken = default(CancellationToken))
        {
            // Tracing
            bool _shouldTrace = ServiceClientTracing.IsEnabled;
            string _invocationId = null;
            if (_shouldTrace)
            {
                _invocationId = ServiceClientTracing.NextInvocationId.ToString();
                Dictionary<string, object> tracingParameters = new Dictionary<string, object>();
                tracingParameters.Add("cancellationToken", cancellationToken);
                ServiceClientTracing.Enter(_invocationId, this, "GetInvalid", tracingParameters);
            }
            // Construct URL
            var _baseUrl = this.Client.BaseUri.AbsoluteUri;
            var _url = new Uri(new Uri(_baseUrl + (_baseUrl.EndsWith("/") ? "" : "/")), "duration/invalid").ToString();
            List<string> _queryParameters = new List<string>();
            if (_queryParameters.Count > 0)
            {
                _url += "?" + string.Join("&", _queryParameters);
            }
            // Create HTTP transport objects
            HttpRequestMessage _httpRequest = new HttpRequestMessage();
            HttpResponseMessage _httpResponse = null;
            try
            {
                _httpRequest.Method = new HttpMethod("GET");
                _httpRequest.RequestUri = new Uri(_url);
                // Set Headers
                if (this.Client.GenerateClientRequestId != null && this.Client.GenerateClientRequestId.Value)
                {
                    _httpRequest.Headers.TryAddWithoutValidation("x-ms-client-request-id", Guid.NewGuid().ToString());
                }
                if (this.Client.AcceptLanguage != null)
                {
                    if (_httpRequest.Headers.Contains("accept-language"))
                    {
                        _httpRequest.Headers.Remove("accept-language");
                    }
                    _httpRequest.Headers.TryAddWithoutValidation("accept-language", this.Client.AcceptLanguage);
                }
                if (customHeaders != null)
                {
                    foreach(var _header in customHeaders)
                    {
                        if (_httpRequest.Headers.Contains(_header.Key))
                        {
                            _httpRequest.Headers.Remove(_header.Key);
                        }
                        _httpRequest.Headers.TryAddWithoutValidation(_header.Key, _header.Value);
                    }
                }

                // Serialize Request
                string _requestContent = null;
                // Set Credentials
                if (this.Client.Credentials != null)
                {
                    cancellationToken.ThrowIfCancellationRequested();
                    await this.Client.Credentials.ProcessHttpRequestAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                }
                // Send Request
                if (_shouldTrace)
                {
                    ServiceClientTracing.SendRequest(_invocationId, _httpRequest);
                }
                cancellationToken.ThrowIfCancellationRequested();
                _httpResponse = await this.Client.HttpClient.SendAsync(_httpRequest, cancellationToken).ConfigureAwait(false);
                if (_shouldTrace)
                {
                    ServiceClientTracing.ReceiveResponse(_invocationId, _httpResponse);
                }
                HttpStatusCode _statusCode = _httpResponse.StatusCode;
                cancellationToken.ThrowIfCancellationRequested();
                string _responseContent = null;
                if ((int)_statusCode != 200)
                {
                    var ex = new ErrorException(string.Format("Operation returned an invalid status code '{0}'", _statusCode));
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        Error _errorBody = SafeJsonConvert.DeserializeObject<Error>(_responseContent, this.Client.DeserializationSettings);
                        if (_errorBody != null)
                        {
                            ex.Body = _errorBody;
                        }
                    }
                    catch (JsonException)
                    {
                        // Ignore the exception
                    }
                    ex.Request = new HttpRequestMessageWrapper(_httpRequest, _requestContent);
                    ex.Response = new HttpResponseMessageWrapper(_httpResponse, _responseContent);
                    if (_shouldTrace)
                    {
                        ServiceClientTracing.Error(_invocationId, ex);
                    }
                    throw ex;
                }
                // Create Result
                var _result = new AzureOperationResponse<TimeSpan?>();
                _result.Request = _httpRequest;
                _result.Response = _httpResponse;
                if (_httpResponse.Headers.Contains("x-ms-request-id"))
                {
                    _result.RequestId = _httpResponse.Headers.GetValues("x-ms-request-id").FirstOrDefault();
                }
                // Deserialize Response
                if ((int)_statusCode == 200)
                {
                    try
                    {
                        _responseContent = await _httpResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
                        _result.Body = SafeJsonConvert.DeserializeObject<TimeSpan?>(_responseContent, this.Client.DeserializationSettings);
                    }
                    catch (JsonException ex)
                    {
                        throw new RestException("Unable to deserialize the response.", ex);
                    }
                }
                if (_shouldTrace)
                {
                    ServiceClientTracing.Exit(_invocationId, _result);
                }
                return _result;
            }
            catch
            {
                _httpRequest.Dispose();
                if (_httpResponse != null)
                {
                    _httpResponse.Dispose();
                }
                throw;
            }    
        }

    }
}
